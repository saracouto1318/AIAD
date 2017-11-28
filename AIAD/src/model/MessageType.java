package model;

public enum MessageType {
	NEW {
		public Class<?> getMessageClass() {
			return Message.class;
		}
		public boolean canCastAs(Message message) {
			return NewRequest.class.isInstance(message);
		}
	}, ANSWER {
		public Class<?> getMessageClass() {
			return Message.class;
		}
		public boolean canCastAs(Message message) {
			return AnswerRequest.class.isInstance(message);
		}
	}, SATISFIED {
		public Class<?> getMessageClass() {
			return Message.class;
		}
		public boolean canCastAs(Message message) {
			return SatisfiedRequest.class.isInstance(message);
		}
	}, RENEGOTIATE {
		public Class<?> getMessageClass() {
			return Message.class;
		}
		public boolean canCastAs(Message message) {
			return RenegotiateRequest.class.isInstance(message);
		}
	}, ACK {
		public Class<?> getMessageClass() {
			return Message.class;
		}
		public boolean canCastAs(Message message) {
			return AckRequest.class.isInstance(message);
		}
	}, REJ {
		public Class<?> getMessageClass() {
			return Message.class;			
		}
		public boolean canCastAs(Message message) {
			return RejRequest.class.isInstance(message);
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
		else if(ACK.canCastAs(message))
			return ACK;
		else
			return REJ;
	}
}
