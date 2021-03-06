/*
 * Copyright 2000-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.debugger.streams.trace.dsl.impl.java

import com.intellij.debugger.streams.trace.dsl.Convertable
import com.intellij.debugger.streams.trace.dsl.Expression
import com.intellij.debugger.streams.trace.dsl.ForLoopBody
import com.intellij.debugger.streams.trace.dsl.VariableDeclaration

/**
 * @author Vitaliy.Bibaev
 */
class JavaForLoop(private val initialization: VariableDeclaration,
                  private val condition: Expression,
                  private val afterThought: Expression,
                  private val loopBody: ForLoopBody) : Convertable {
  override fun toCode(indent: Int): String {
    return "for (${initialization.toCode()}; ${condition.toCode()}; ${afterThought.toCode()}) {\n".withIndent(indent) +
           loopBody.toCode(indent + 1) +
           "}".withIndent(indent)
  }
}