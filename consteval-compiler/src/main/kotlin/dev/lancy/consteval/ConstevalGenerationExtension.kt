package dev.lancy.consteval

import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.declarations.IrValueParameter
import org.jetbrains.kotlin.ir.expressions.IrBody
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrReturn
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.isBoolean
import org.jetbrains.kotlin.ir.types.isInt
import org.jetbrains.kotlin.ir.types.isString
import org.jetbrains.kotlin.ir.util.statements
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid

class ConstevalGenerationExtension : IrGenerationExtension, IrElementTransformerVoid() {
    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        moduleFragment.transformChildrenVoid()
    }

    /**
     * Whether constant evaluation supports this type.
     *
     * In the future, this should be configurable, support more types, and consider type parameters, and so on. For
     * now, it is a dumb conditional on the constant types specified in the spec.
     *
     * @param type The type to check.
     * @return Whether the type is supported.
     */
    private fun isSupportedType(type: IrType) = type.isInt() || type.isBoolean() || type.isString()

    /**
     * Attempts to evaluate a body of code with given variables.
     *
     * @param variables The variables to use in the body. This list may be mutated.
     * @param body The body to evaluate.
     * @return The constant result of evaluation, or null if constant evaluation failed and/or is not possible.
     */
    private fun evaluateBody(variables: MutableList<IrValueParameter>, body: IrBody): IrConst<*>? {
        for (statement in body.statements) {
            val result = evaluateStatement(variables, statement)
            result?.let { return it }
        }

        return null
    }

    /**
     * Attempts to evaluate a statement.
     *
     * @param variables The variables to use in the statement. This list may be mutated.
     * @param statement The statement to evaluate.
     * @return The constant result of evaluation, or null if constant evaluation failed and/or is not possible.
     */
    private fun evaluateStatement(variables: MutableList<IrValueParameter>, statement: IrStatement): IrConst<*>? {
        return when (statement) {
            is IrReturn -> evaluateStatement(variables, statement.value)
            is IrConst<*> -> statement

            // Otherwise, the statement is not supported.
            else -> null
        }
    }

    /**
     * Attempts to evaluate a call expression.
     *
     * @param expression The call expression to evaluate.
     * @return The constant result of evaluation, or null if constant evaluation failed and/or is not possible.
     */
    private fun evaluateCall(expression: IrCall): IrConst<*>? {
        val function = expression.symbol.owner

        // Per spec, only process calls to functions beginning with `eval`.
        if (!function.name.identifier.startsWith("eval")) return null

        // Per spec, only process functions with "constant" input and output types.
        if (function.valueParameters.any { !isSupportedType(it.type) })
            return null

        if (!isSupportedType(function.returnType)) return null

        // Having body == null probably means an abstract, expect, or some such function. We do not support these.
        val subVariables = function.valueParameters.toMutableList()
        return function.body?.let { evaluateBody(subVariables, it) }
    }

    /**
     * Visit a call expression. If the following conditions are met, then we will attempt to constant-evaluate the call:
     * 1. The function name starts with `eval`.
     * 2. The function has "constant" input and output types. See [isSupportedType].
     * 3. The function has a body.
     *
     * There are other conditions to be checked in reality, but for the sake of simplicity, we ignore them for now.
     * These may include checking for type parameters, extra information passed by receivers, and so on.
     */
    override fun visitCall(expression: IrCall): IrExpression = evaluateCall(expression) ?: super.visitCall(expression)
}
