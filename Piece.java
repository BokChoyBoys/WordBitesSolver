public class Piece {
    private char[][] _chars;
    private int _pieceType;
    public Piece(char a) {
        _chars = new char[2][2];
        _chars[0][0] = a;
        _pieceType = 0;
    }
    public Piece(char a, char b, boolean isHorizontal) {
        _chars = new char[2][2];
        _chars[0][0] = a;
        if (isHorizontal) {
            _chars[0][1] = b;
            _pieceType = 1;
        } else {
            _chars[1][0] = b;
            _pieceType = 2;
        }
    }
    public int getpieceType() {
        return _pieceType;
    }
    public char getChar(int x) {
        if (_pieceType == 1) {
            return _chars[0][x];
        } else if (_pieceType == 2) {
            return _chars[x][0];
        } else {
            return _chars[0][0];
        }
    }
    public String toString() {
        if (_pieceType == 1) {
            return "" + _chars[0][0] + _chars[0][1];
        } else if (_pieceType == 2) {
            return "" + _chars[0][0] + _chars[1][0];
        } else {
            return "" + _chars[0][0];
        }
    }
}
