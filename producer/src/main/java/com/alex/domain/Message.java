package com.alex.domain;

import java.util.List;

/**
 * Created by Shishkov A.V. on 24.09.18.
 */
public class Message {
	private String command;
	private String name;
	private String method;
	private String className;
	private String kFold;
	private List<Fingerprint> fingerprints;
	private String expectedVersion;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getkFold() {
		return kFold;
	}

	public void setkFold(String kFold) {
		this.kFold = kFold;
	}

	public List<Fingerprint> getFingerprints() {
		return fingerprints;
	}

	public void setFingerprints(List<Fingerprint> fingerprints) {
		this.fingerprints = fingerprints;
	}

	public String getExpectedVersion() {
		return expectedVersion;
	}

	public void setExpectedVersion(String expectedVersion) {
		this.expectedVersion = expectedVersion;
	}
}
