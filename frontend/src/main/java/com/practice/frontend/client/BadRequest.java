package com.practice.frontend.client;

import java.util.List;
import java.util.Objects;

public class BadRequest extends RuntimeException{

		

		private final List<String> errors;
		
		public BadRequest(List<String> errors) {
			this.errors=errors;
		}
		
	
		public BadRequest(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,List<String> errors) {
			super(message, cause, enableSuppression, writableStackTrace);
			this.errors=errors;
			
			
		}
		public BadRequest(String message, Throwable cause,List<String> errors) {
			
			super(message, cause);
			this.errors=errors;
		}
		public BadRequest(String message,List<String> errors) {
			super(message);
			this.errors=errors;
			
		}
		public BadRequest(Throwable cause,List<String> errors) {
			super(cause);
			this.errors=errors;
		
		}


		@Override
		public int hashCode() {
			return Objects.hash(errors);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			BadRequest other = (BadRequest) obj;
			return Objects.equals(errors, other.errors);
		}
		@Override
		public String toString() {
			return "BadRequest [errors=" + errors + "]";
		}
}
