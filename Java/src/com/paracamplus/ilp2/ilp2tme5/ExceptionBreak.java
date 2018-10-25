package com.paracamplus.ilp2.ilp2tme5;

public class ExceptionBreak extends Exception {

	private static final long serialVersionUID = -1595354352885380939L;
	
	private String label;

	public String getEtiquette() {
		return label;
	}

	public ExceptionBreak(String label) {
		this.label = label;
	}
}
