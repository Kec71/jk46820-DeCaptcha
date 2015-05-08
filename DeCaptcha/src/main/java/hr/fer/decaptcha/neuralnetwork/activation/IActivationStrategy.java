package hr.fer.decaptcha.neuralnetwork.activation;

public interface IActivationStrategy {

	public double activate(double weightedSum);
	public double derivative(double weightedSum);
}
