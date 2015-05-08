package hr.fer.decaptcha.neuralnetwork.activation;

public class LinearActivationStrategy implements IActivationStrategy {

	@Override
	public double activate(double weightedSum) {
		return weightedSum;
	}

	@Override
	public double derivative(double weightedSum) {
		return 1;
	}

}
