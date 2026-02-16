package tcw.energy;

public enum CableTier {
    BASIC(64, 2, 16000, "basic"),
    REINFORCED(256, 1, 64000, "reinforced"),
    CRYO(1024, 0, 256000, "cryo");

    public final int transferRate;
    public final int lossPerTransfer;
    public final int storage;
    public final String textureSuffix;

    CableTier(int transferRate, int lossPerTransfer, int storage, String textureSuffix) {
        this.transferRate = transferRate;
        this.lossPerTransfer = lossPerTransfer;
        this.storage = storage;
        this.textureSuffix = textureSuffix;
    }
}
