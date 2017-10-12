package command.delta;

public enum DeltaSubCommand {
	INVALID_POS((byte)0),
	POS_001((byte)1),
	POS_002((byte)2),
	POS_003((byte)3),
	POS_004((byte)4),
	POS_005((byte)5),
	POS_006((byte)6),
	POS_007((byte)7),
	POS_008((byte)8),
	POS_009((byte)9),
	POS_010((byte)10);
	
	private byte sub;
	
	private DeltaSubCommand(byte sub){
		this.sub = sub;
	}

	public byte getSub() {
		return sub;
	}

	public void setSub(byte sub) {
		this.sub = sub;
	}
	
}
