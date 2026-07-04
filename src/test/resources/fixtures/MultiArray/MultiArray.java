public class MultiArray {
    public static void main(String[] args) {
        int[][] grid = new int[3][4];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j] = i * 10 + j;
            }
        }
        int sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                sum += grid[i][j];
            }
        }
        System.out.println(sum);
        System.out.println(grid.length);
        System.out.println(grid[0].length);
        System.out.println(grid[2][3]);

        String[][] names = new String[2][2];
        names[0][0] = "a";
        names[1][1] = "b";
        System.out.println(names[0][0] + names[1][1]);
        System.out.println(names[0][1] == null);

        long[][][] cube = new long[2][3][4];
        cube[1][2][3] = 99L;
        System.out.println(cube.length + " " + cube[0].length + " " + cube[0][0].length);
        System.out.println(cube[1][2][3]);
        System.out.println(cube[0][0][0]);
        System.out.println("done");
    }
}
