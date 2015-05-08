package hr.fer.decaptcha.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

public class Neuron {
	
	List<Synapse> synapses; 
	
	public Neuron() {
		synapses = new ArrayList<>();
	}

	/**
	 * Constructor which constructs and initializes neuron.
	 * If neuron is in input layer than this method won't set any bias for this neuron. 
	 * @param inInputLayer flag which determines whether this neuron is located in input layer.
	 */
	public Neuron(boolean inInputLayer) {
		
	}

}
