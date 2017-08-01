package nexus.op

import nexus._

/**
 * Matrix multiplication of two matrices (2-D tensors).
 * @note The second axis of the first operand and the first axis of the second operand must match.
 * @author Tongfei Chen
 * @since 0.1.0
 */
object MMul extends PolyOp2[MMulF]

@impMsg("Cannot apply MMul to ${X1} and ${X2}.")
trait MMulF[X1, X2, Y] extends Op2[X1, X2, Y] {
  def name = "MMul"
}

object MMulF {
  implicit def MMulImpl[UT[D], T[D, _ <: $$], D, A, B, C](implicit env: Env[T, D]): MMulF[T[D, A::B::$], T[D, B::C::$], T[D, A::C::$]] =
    new MMulF[T[D, A::B::$], T[D, B::C::$], T[D, A::C::$]] {
      import env._
      def forward(x1: T[D, A::B::$], x2: T[D, B::C::$]) = ???
      def backward1(dy: T[D, A::C::$], y: T[D, A::C::$], x1: T[D, A::B::$], x2: T[D, B::C::$]) = ???
      def backward2(dy: T[D, A::C::$], y: T[D, A::C::$], x1: T[D, A::B::$], x2: T[D, B::C::$]) = ???
    }
}