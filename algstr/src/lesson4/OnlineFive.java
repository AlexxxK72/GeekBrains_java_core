package lesson4;

public class OnlineFive {

    private static void iterPrint(int i) {
        while (i >= 0) {
            System.out.print(i-- + " ");
        }
        System.out.print(" i:" + i);
    }

    private static void recPrint(int i) {
        if (i >= 0) {
            System.out.print(i + " ");
            recPrint(--i);
        }
        System.out.print(" i:" + i);
    }

    static int count = 0;
    private static int routes(int x, int y) {
        count++;
        if (x == 0 && y == 0) return 0;
        if (x == 0 || y == 0) return 1;
        return routes(x - 1, y) + routes(x, y - 1);
    }

    static int X = 8;
    static int Y = 8;
    static int QUEENS = 8;
    static int board[][] = new int[Y][X];

    static void init() {
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                board[i][j] = 0;
            }
        }
    }

    static void printBoard() {
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean checkQueen(int x, int y) {
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                if (board[i][j] != 0)
                    if (!(i == x && j == y)) {
                        if (i - x == 0 || j - y == 0) return false;
                        if (Math.abs(i - x) == Math.abs(j - y)) return false;
                    }
            }
        }
        return true;
    }

    static boolean checkBoard() {
        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                if (board[i][j] != 0)
                    if (!checkQueen(i, j))
                        return false;
            }
        }
        return true;
    }

    private static boolean queens(int n) {
        if (!checkBoard()) return false;
        if (n == QUEENS + 1) return true;

        for (int i = 0; i < Y; i++) {
            for (int j = 0; j < X; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = n;
                    if (queens(n + 1))
                        return true;
                    board[i][j] = 0;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        /* */
        init();
        queens(1);
        printBoard();

//        iterPrint(2);
//        System.out.println();
//        recPrint(2);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.printf("%6d", routes(i, j));
            }
            System.out.println();
        }
        System.out.println("\n" + count);
    }
}
