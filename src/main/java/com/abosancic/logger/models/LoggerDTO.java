/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abosancic.logger.models;

import java.util.Objects;

/**
 *
 * @author abosancic
 */
public class LoggerDTO {
	
	private String level;
	
	private String logger;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLogger() {
		return logger;
	}

	public void setLogger(String logger) {
		this.logger = logger;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 47 * hash + Objects.hashCode(this.logger);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final LoggerDTO other = (LoggerDTO) obj;
		if (!Objects.equals(this.logger, other.logger)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder toStringBuilder;
		toStringBuilder = new StringBuilder("LoggerDTO{");
		toStringBuilder.append("level=").append(this.level);
		toStringBuilder.append(",logger=").append(this.logger);
		toStringBuilder.append('}');
		return toStringBuilder.toString();
	}
	
}
