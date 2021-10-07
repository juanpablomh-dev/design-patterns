package com.jpmh.builder;

public final class SubWay {

	private final BreadType breadType;
	private final MeatType meatType;
	private final Boolean mayonnaise;
	private final Boolean moztaza;
	private final Boolean ketchup;
	private final Boolean lettuce;
	private final Boolean tomato;
	private final Boolean onion;
	private final Boolean pickle;
	private final Boolean ham;
	private final Boolean cheese;
	private final Boolean bacon;

	private SubWay(Builder builder) {
		this.breadType = builder.breadType;
		this.meatType = builder.meatType;
		this.mayonnaise = builder.mayonnaise;
		this.moztaza = builder.moztaza;
		this.ketchup = builder.ketchup;
		this.lettuce = builder.lettuce;
		this.tomato = builder.tomato;
		this.onion = builder.onion;
		this.pickle = builder.pickle;
		this.ham = builder.ham;
		this.cheese = builder.cheese;
		this.bacon = builder.bacon;
	}

	public BreadType getBreadType() {
		return breadType;
	}

	public MeatType getMeatType() {
		return meatType;
	}

	public Boolean getMayonnaise() {
		return mayonnaise;
	}

	public Boolean getMoztaza() {
		return moztaza;
	}

	public Boolean getKetchup() {
		return ketchup;
	}

	public Boolean getLettuce() {
		return lettuce;
	}

	public Boolean getTomato() {
		return tomato;
	}

	public Boolean getOnion() {
		return onion;
	}

	public Boolean getPickle() {
		return pickle;
	}

	public Boolean getHam() {
		return ham;
	}

	public Boolean getCheese() {
		return cheese;
	}

	public Boolean getBacon() {
		return bacon;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("SubWay of ").append(breadType).append(" bread and ").append(meatType);
		if (mayonnaise != null) {
			sb.append(" and mayonnaise");
		}
		if (moztaza != null) {
			sb.append(" and moztaza");
		}
		if (ketchup != null) {
			sb.append(" and ketchup");
		}
		if (lettuce != null) {
			sb.append(" and lettuce");
		}
		if (tomato != null) {
			sb.append(" and tomato");
		}
		if (onion != null) {
			sb.append(" and onion");
		}
		if (pickle != null) {
			sb.append(" and pickle");
		}
		if (ham != null) {
			sb.append(" and ham");
		}
		if (cheese != null) {
			sb.append(" and cheese");
		}
		if (bacon != null) {
			sb.append(" and bacon");
		}
		sb.append('.');
		return sb.toString();
	}

	/**
	 * The builder class.
	 */
	public static class Builder {

		private final BreadType breadType;
		private final MeatType meatType;
		private Boolean mayonnaise;
		private Boolean moztaza;
		private Boolean ketchup;
		private Boolean lettuce;
		private Boolean tomato;
		private Boolean onion;
		private Boolean pickle;
		private Boolean ham;
		private Boolean cheese;
		private Boolean bacon;

		/**
		 * Constructor.
		 */
		public Builder(BreadType breadType, MeatType meatType) {
			if (breadType == null || meatType == null) {
				throw new IllegalArgumentException("breadType and meatType can not be null");
			}
			this.breadType = breadType;
			this.meatType = meatType;
		}

		public Builder withMayonnaise(Boolean mayonnaise) {
			this.mayonnaise = mayonnaise;
			return this;
		}

		public Builder withMoztaza(Boolean moztaza) {
			this.moztaza = moztaza;
			return this;
		}

		public Builder withKetchup(Boolean ketchup) {
			this.ketchup = ketchup;
			return this;
		}

		public Builder withLettuce(Boolean lettuce) {
			this.lettuce = lettuce;
			return this;
		}

		public Builder withTomato(Boolean tomato) {
			this.tomato = tomato;
			return this;
		}

		public Builder withOnion(Boolean onion) {
			this.onion = onion;
			return this;
		}

		public Builder withPickle(Boolean pickle) {
			this.pickle = pickle;
			return this;
		}

		public Builder withHam(Boolean ham) {
			this.ham = ham;
			return this;
		}

		public Builder withCheese(Boolean cheese) {
			this.cheese = cheese;
			return this;
		}

		public Builder withBacon(Boolean bacon) {
			this.bacon = bacon;
			return this;
		}

		public SubWay build() {
			return new SubWay(this);
		}
	}
}
