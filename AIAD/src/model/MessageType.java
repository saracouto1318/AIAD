package model;

/**
 * 
 * Enumerates the different message's types
 *
 */
public enum MessageType {
	NEW {
		/**
		 * Gets the message's class
		 * @return The message's class
		 */
		public Class<?> getMessageClass() {
			return NewRequest.class;
		}
		/**
		 * Verifies if a message can be casted as NewRequest
		 * @param message Message that will be casted
		 * @return true if the message can be a instance of NewRequest
		 */
		public boolean canCastAs(Message message) {
			return message instanceof NewRequest;
		}
	}, ANSWER {
		/**
		 * Gets the message's class
		 * @return The message's class
		 */
		public Class<?> getMessageClass() {
			return AnswerRequest.class;
		}
		/**
		 * Verifies if a message can be casted as AnswerRequest
		 * @param message Message that will be casted
		 * @return true if the message can be a instance of AnswerRequest
		 */
		public boolean canCastAs(Message message) {
			return message instanceof AnswerRequest;
		}
	}, SATISFIED {
		/**
		 * Gets the message's class
		 * @return The message's class
		 */
		public Class<?> getMessageClass() {
			return SatisfiedRequest.class;
		}
		/**
		 * Verifies if a message can be casted as SatisfiedRequest
		 * @param message Message that will be casted
		 * @return true if the message can be a instance of SatisfiedRequest
		 */
		public boolean canCastAs(Message message) {
			return message instanceof SatisfiedRequest;
		}
	}, RENEGOTIATE {
		/**
		 * Gets the message's class
		 * @return The message's class
		 */
		public Class<?> getMessageClass() {
			return RenegotiateRequest.class;
		}
		/**
		 * Verifies if a message can be casted as RenegotiateRequest
		 * @param message Message that will be casted
		 * @return true if the message can be a instance of RenegotiateRequest
		 */
		public boolean canCastAs(Message message) {
			return message instanceof RenegotiateRequest;
		}
	}, STATUS {
		/**
		 * Gets the message's class
		 * @return The message's class
		 */
		public Class<?> getMessageClass() {
			return StatusRequest.class;
		}
		/**
		 * Verifies if a message can be casted as StatusRequest
		 * @param message Message that will be casted
		 * @return true if the message can be a instance of StatusRequest
		 */
		public boolean canCastAs(Message message) {
			return message instanceof StatusRequest;
		}
	};
	
	/**
	 * Gets the message's class
	 * @return The message's class
	 */
	public abstract Class<?> getMessageClass();
	
	/**
	 * Verifies if a message can be casted as a certain type
	 * @param message Message that will be casted
	 * @return true if the message can be a instance of a certain type
	 */
	public abstract boolean canCastAs(Message message);
	
	/**
	 * Gets the message's type
	 * @param message Message that will be analyzed
	 * @return The message's type
	 */
	public static MessageType getMessageType(Message message) {
		if(NEW.canCastAs(message))
			return NEW;
		else if(ANSWER.canCastAs(message))
			return ANSWER;
		else if(SATISFIED.canCastAs(message))
			return SATISFIED;
		else if(RENEGOTIATE.canCastAs(message))
			return RENEGOTIATE;
		else 
			return STATUS;
	}
}
