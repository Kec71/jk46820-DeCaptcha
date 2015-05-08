package hr.fer.decaptcha.neuralnetwork;


public class NeuralNetwork {
	
	private Layer inputLayer;
	private Layer hiddenLayer;
	private Layer outputLayer;
	
	public NeuralNetwork(final int inputLayerSize, final int hiddenLayerSize, final int outputLayerSize) {
		
		inputLayer = new Layer();
		hiddenLayer = new Layer();
		outputLayer = new Layer();
		
		inputLayer.setNextLayer(hiddenLayer);
		
		hiddenLayer.setPreviousLayer(inputLayer);
		hiddenLayer.setNextLayer(outputLayer);
		
		outputLayer.setPreviousLayer(hiddenLayer);
		
		inputLayer.initializeLayer(inputLayerSize);
		hiddenLayer.initializeLayer(hiddenLayerSize);
		outputLayer.initializeLayer(outputLayerSize);
	}
	
}
