public enum Direction {
    NORTH(2), EAST(3), SOUTH(0), WEST(1);
    private int frameLineNumber;
    Direction(int frameLineNumber) {
        this.frameLineNumber = frameLineNumber;
    }
    public int getFrameLineNumber() {
        return frameLineNumber;
    }
}
