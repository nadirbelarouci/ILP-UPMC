package com.paracamplus.ilp2.ilp2tme5;

public class ExceptionContinue extends Exception {

	private static final long serialVersionUID = -6070386198196100349L;
	
	private String label;

	public String getEtiquette() {
		return label;
	}

	public ExceptionContinue(String label) {
		this.label = label;
	}

}
