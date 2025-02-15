package com.epita.hivers

import com.epita.hivers.provider.Prototype
import com.epita.hivers.test.Nested
import com.epita.hivers.test.TestService
import com.epita.hivers.test.TestServiceBlipImpl
import com.epita.hivers.test.TestServiceImpl
import com.epita.hivers.core.*
import org.junit.Test
import java.util.function.Supplier
import kotlin.reflect.jvm.javaMethod

class BasicHiversTest {

    @Test
    fun `test basic hivers use case`() {
        val hivers = Hivers {

            // Add a provider to the scope, of type prototype, that binds on Nested.class.
            provider(Nested::class.java, Prototype(Nested::class.java, Supplier { Nested(instanceOf(TestService::class.java)) }))

            // Shortcut for singleton addition.
            bean(TestService::class.java, TestServiceImpl())

            // Shortcurt for singleton, binding on the element class.
            bean(Nested(instanceOf(TestService::class.java)))

            // Stacks a scope.
            scope {

                // Adds a singleton
                bean(TestService::class.java, TestServiceBlipImpl()) {

                    // Define AoP behaviour on the before(Pong)
                    before(TestService::pong.javaMethod) { println("before >> ") }

                    // Define AoP behaviour around calls to the pong method.

                    around(TestService::pong.javaMethod) { ctx ->
                        val before = System.nanoTime()
                        val res: Any? = ctx.proceed()
                        println("Method ${ctx.method.name} executed in ${(System.nanoTime() - before) / 1000000.0}ms")
                        res
                    }

                    // Define AoP behaviour around calls to the pong method. Wraps previous aspects.
                    around(TestService::pong.javaMethod) { ctx ->
                        val res: Any? = ctx.proceed()
                        println("result is: $res")
                        res
                    }

                    // Adds behaviour after the calls to pong.
                    after(TestService::pong.javaMethod) { println("<< after !") }
                }

                // Define AoP behaviour around calls to the pong method.
                //provider(Nested::class.java, Singleton(Nested::class.java, Supplier { Nested(instanceOf(TestService::class.java)) }))
                //provider(Nested::class.java, Prototype(Nested::class.java, Supplier { Nested(instanceOf(TestService::class.java)) }))
            }
        }

        // Test call.
        val testService: TestService = hivers.instanceOf(TestService::class.java)
        5 timesDo { testService.ping() }
        Thread.sleep(10)

        5 timesDo { testService.pong() }
        Thread.sleep(10)
    }
}

private infix fun Int.timesDo(function: () -> Unit) {
    for (i in 1..this) {
        function()
    }
}
