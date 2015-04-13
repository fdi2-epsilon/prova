package eu.unipv.epsilon.scells

// This trait is mixed in Model and adds elements to Evaluator's operations map
trait Arithmetic { this: Evaluator =>

  // Binary operations expect exactly lists of two elements,
  // a different number throws a MatchError that will be caught by 'Evaluator.evaluate'.

  operations += (
    "add"  -> { case List(x, y) => x + y },
    "sub"  -> { case List(x, y) => x - y },
    "div"  -> { case List(x, y) => x / y },
    "mul"  -> { case List(x, y) => x * y },
    "mod"  -> { case List(x, y) => x % y },
    "sum"  -> { xs => (0.0 /: xs)(_ + _) },
    "prod" -> { xs => (0.0 /: xs)(_ * _) }
  )

}
