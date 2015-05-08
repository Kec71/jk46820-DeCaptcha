package hr.fer.decaptcha.neuralnetwork;

/**
 * Class which represents synapse in neural network.
 * Synapse is defined with neuron which it's attached to and weight factor which determines
 * how big influence this synapse has to neuron output.
 * @author Janko
 *
 */
public class Synapse {
	
	private Neuron sourceNeuron;
	/** Numerical value which represents importance of this synapse to source neuron */
	private double weight;
	
	public Synapse(Neuron sourceNeuron, double weight) {
		this.sourceNeuron = sourceNeuron;
		this.weight = weight;
	}

	public Neuron getSourceNeuron() {
		return sourceNeuron;
	}

	public void setSourceNeuron(Neuron sourceNeuron) {
		this.sourceNeuron = sourceNeuron;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
