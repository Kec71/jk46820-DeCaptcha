package hr.fer.decaptcha.neuralnetwork.activation;

public class SigmoidActivationStrategy implements IActivationStrategy {

	@Override
	public double activate(double weightedSum) {
		return 1.0 / (1 + Math.exp(-1.0 * weightedSum));
	}

	@Override
	public double derivative(double weightedSum) {
		return 0;
	}

}
