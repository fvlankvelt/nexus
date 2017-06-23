package nexus.op.loss

import nexus._
import nexus.cpu._
import shapeless._

/**
 * @author Tongfei Chen
 */
object CrossEntropyLoss extends GenOp2[CrossEntropyLossF]

trait CrossEntropyLossF[YP, YG, L] extends Op2[YP, YG, L] {
  def name = "CrossEntropyLoss"
}

object CrossEntropyLossF {

  class CPUCrossEntropyLossF[D](env: Env[cpu.UntypedDenseTensor, D]) extends CrossEntropyLossF[cpu.DenseTensor[D, HNil], cpu.DenseTensor[D, HNil], cpu.DenseTensor[D, HNil]] {
    def forward(yp: DenseTensor[D, HNil], yg: DenseTensor[D, HNil]) = {
      env.add(
        env.mul(yg, env.log(yp)),
        env.mul(env.addS(env.neg(yg), env.one), env.log(env.addS(env.neg(yp), env.one)))
      )
    }

    def backward1(dl: DenseTensor[D, HNil], l: DenseTensor[D, HNil], yp: DenseTensor[D, HNil], yg: DenseTensor[D, HNil]) = {
      env.mul(
        dl,
        env.sub(
          env.div(yg, yp),
          env.div(env.addS(env.neg(yg), env.one), env.addS(env.neg(yp), env.one))
        )
      )
    }

    def backward2(dy: DenseTensor[D, HNil], y: DenseTensor[D, HNil], x1: DenseTensor[D, HNil], x2: DenseTensor[D, HNil]) = ???
  }

  implicit def cpuLogLossF[D](implicit env: Env[cpu.UntypedDenseTensor, D]): CrossEntropyLossF[cpu.DenseTensor[D, HNil], cpu.DenseTensor[D, HNil], cpu.DenseTensor[D, HNil]] = new CPUCrossEntropyLossF(env)

}