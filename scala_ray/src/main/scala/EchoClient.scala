package org.example.application

import io.ray.api.PyActorHandle
import io.ray.api.function.PyActorMethod
import org.testng.Assert


class EchoClient(val id: Int) {
  def addEcho(py_echo_handle: PyActorHandle): Int = {
    val objRef1 = py_echo_handle.task(PyActorMethod.of("save", classOf[java.lang.Integer]), "hello world " + id.toString).remote()
    Assert.assertEquals(objRef1.get(), 0)
    0
  }
}
