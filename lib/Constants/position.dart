class Position {
  int x;
  int y;

  Position(this.x, this.y);

  void setX(int x) {
    this.x = x;
  }

  void setY(int y) {
    this.y = y;
  }

  int getX() {
    return x;
  }

  int getY() {
    return y;
  }

  @override
  bool operator ==(Object other) {
    if (identical(this, other)) return true;

    if (other is! Position) return false;

    return x == other.x && y == other.y;
  }

  @override
  int get hashCode => 31 * x + y;

  @override
  String toString() {
    return '($x, $y)';
  }
}
