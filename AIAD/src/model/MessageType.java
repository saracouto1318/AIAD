package model;

public enum MessageType {
	NEW {
		public Class<?> getMessageClass() {
			return NewRequest.class;
		}
		public boolean canCastAs(Message message) {
			return message instanceof NewRequest;
		}
	}, ANSWER {
		public Class<?> getMessageClass() {
			return AnswerRequest.class;
		}
		public boolean canCastAs(Message message) {
			return message instanceof AnswerRequest;
		}
	}, SATISFIED {
		public Class<?> getMessageClass() {
			return SatisfiedRequest.class;
		}
		public boolean canCastAs(Message message) {
			return message instanceof SatisfiedRequest;
		}
	}, RENEGOTIATE {
		public Class<?> getMessageClass() {
			return RenegotiateRequest.class;
		}
		public boolean canCastAs(Message message) {
			return message instanceof RenegotiateRequest;
		}
	}, STATUS {
		public Class<?> getMessageClass() {
			return StatusRequest.class;
		}
		public boolean canCastAs(Message message) {
			return message instanceof StatusRequest;
		}
	};
	
	public abstract Class<?> getMessageClass();
	
	public abstract boolean canCastAs(Message message);
	
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
