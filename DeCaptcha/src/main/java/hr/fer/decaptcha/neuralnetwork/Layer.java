package hr.fer.decaptcha.neuralnetwork;

import java.util.ArrayList;
import java.util.List;

public class Layer {

	private List<Neuron> neurons;
	private Layer nextLayer;
	private Layer previousLayer;
	
	public Layer() {
	}
	
	public Layer(Layer nextLayer, Layer previousLayer) {
		this.nextLayer = nextLayer;
		this.previousLayer = previousLayer;
	}
	
	public void initializeLayer(int layerSize) {
		
		neurons = new ArrayList<>();

		for(int i = 0; i < layerSize; i++) {
			neurons.add(new Neuron(isInputLayer()));
		}
	}

	
	/**
	 * Method which determines if this layer is input layer.
	 * @return true if this layer is input layer, false otherwise.
	 */
	public boolean isInputLayer() {
		return previousLayer == null ? true : false;
	}
	
	/**
	 * Method which determines if this layer is output layer.
	 * @return true if this layer is output layer, false otherwise.
	 */
	public boolean isOutputLayer() {
		return nextLayer == null ? true : false;
	}
	
	public List<Neuron> getNeurons() {
		return neurons;
	}
	
	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

	public Layer getNextLayer() {
		return nextLayer;
	}

	public void setNextLayer(Layer nextLayer) {
		this.nextLayer = nextLayer;
	}

	public Layer getPreviousLayer() {
		return previousLayer;
	}

	public void setPreviousLayer(Layer previousLayer) {
		this.previousLayer = previousLayer;
	}
}
