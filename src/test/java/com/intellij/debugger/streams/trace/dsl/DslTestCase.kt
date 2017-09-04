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
package com.intellij.debugger.streams.trace.dsl

import com.intellij.testFramework.UsefulTestCase
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

/**
 * @author Vitaliy.Bibaev
 */
abstract class DslTestCase(private val directoryName: String, private val dsl: Dsl) : LightCodeInsightFixtureTestCase() {
  fun testCall() {
    doTest {
      call(+"this", "method")
    }
  }

  fun testCallOneArg() {
    doTest {
      call(+"this", "method", +"10")
    }
  }

  fun testCallManyArgs() {
    doTest {
      call(+"this", "method", +"10", +"20", +"30")
    }
  }

  fun testDeclareMutableVariable() {
    doTest {
      declare(variable("int", "a"), true)
    }
  }

  fun testDeclareImmutableVariable() {
    doTest {
      +variable("int", "a")
    }
  }

  fun testDeclareVariableAndInit() {
    doTest {
      declare(variable("int", "a"), +"10", false)
    }
  }

  fun testUseVariable() {
    doTest {
      declare(variable("int", "a"), +"100", true)
      declare(variable("int", "b"), +"a", false)
    }
  }

  fun testIf() {
    doTest {
      ifBranch(+"true") {
        call(+"receiver", "success")
      }
    }
  }

  fun testIfElse() {
    doTest {
      ifBranch(+"false") {
        call(+"this", "success")
      }.elseBranch {
        call(+"this", "fail")
      }
    }
  }

  fun testIfElseIf() {
    doTest {
      ifBranch(+"false") {
        call(+"this", "success")
      }.elseIfBranch(+"true") {
        call(+"this", "failSuccess")
      }
    }
  }

  fun testIfElseIfElse() {
    doTest {
      ifBranch(+"false") {
        call(+"this", "success")
      }.elseIfBranch(+"true") {
        call(+"this", "failSuccess")
      }.elseBranch {
        call(+"this", "failFail")
      }
    }
  }

  fun testForEach() {
    doTest {
      val objects = declare(variable("List", "objects"), +"getObjects()", false)
      forEachLoop(variable("Object", "object"), objects) {
        +(variable.call("toString"))
      }
    }
  }

  fun testForLoop() {
    doTest {
      val objects = declare(variable("List", "objects"), +"getObjects()", false)
      val i = variable("int", "i")
      forLoop(declaration(i, +"0", true), +"i < $objects.size()", +"i++") {
        +(variable.call("toString"))
      }
    }
  }

  fun testLambdaWithExpression() {
    doTest {
      +lambda("x") {
        +(+argName).call("method")
      }
    }
  }

  fun testLambdaWithCodeBlock() {
    doTest {
      +lambda("y") {
        +(+argName).call("method1")
        +(+argName).call("method2")
      }
    }
  }

  private fun doTest(block: Dsl.() -> Unit) {
    dsl.block()
    check()
  }

  private fun check() {
    val actualText = dsl.toCode()
    val testName = getTestName(true)
    UsefulTestCase.assertSameLinesWithFile("testData/dsl/$directoryName/$testName.out", actualText, false)
  }
}