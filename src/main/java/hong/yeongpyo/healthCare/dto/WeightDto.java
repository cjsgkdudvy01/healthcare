package hong.yeongpyo.healthCare.dto;

import java.sql.Timestamp;

public class WeightDto {
	private int weightno;
	private int weight;
	private Timestamp weightsavatime;
	
	public WeightDto() {}

	public WeightDto(int weightno, int weight, Timestamp weightsavatime) {
		super();
		this.weightno = weightno;
		this.weight = weight;
		this.weightsavatime = weightsavatime;
	}

	public int getWeightno() {
		return weightno;
	}

	public void setWeightno(int weightno) {
		this.weightno = weightno;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Timestamp getWeightsavatime() {
		return weightsavatime;
	}

	public void setWeightsavatime(Timestamp weightsavatime) {
		this.weightsavatime = weightsavatime;
	}
}
