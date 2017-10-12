package command.ycm;

public enum YcmSubCommand {
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
	POS_010((byte)10),
	POS_011((byte)11),
	POS_012((byte)12);
	
	private YcmSubCommand(byte sub){
		this.sub = sub;
	}
	
	private byte sub;
	
	public byte getSub() {
		return sub;
	}

	public void setSub(byte sub) {
		this.sub = sub;
	}
	
}
