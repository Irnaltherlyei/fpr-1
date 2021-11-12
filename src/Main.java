import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * This projects is able to solve a NxN matrix with the Gaussian Elemination Method.
 * A simple GUI-Implementation was added to input a matrix more easy.
 * If "NAN" is displayed the matrix has no solution.
 * If "INFINITY" is displayed the matrix has endless solutions.
 * @author Adrian Kaminski
 * @see <a href="https://github.com/Irnaltherlyei</a>
 */

/**
 * Class Constructor Main with simple main method.
 */
public class Main {
    /**
     * Main
     * @param args normal args
     */
    public static void main(String[] args) {
        // long l = (long)Math.pow(3, 22);
        // System.out.println(l);
        // int i = (int)l;
        // System.out.println(i);

        init();

        //Testing
        // double[][] matrix1 = {{4,3,1,13},
        //                   {2,-5,3,1},
        //                   {7,-1,-2,-1}};
        // double[][] matrix2 = {{1,-1,2,0},
        //         {-2,1,-6,0},
        //         {1,0,-2,3}};
        // double[] res = solve(matrix2);
        // if(res==null) return;
        // for(double var:res){
        //     System.out.println(var);
        // }
    }

    /**
     * init() creates a Java Swing window with GUI Implementation
     *
     * The Button "calculate" initiates the solve(double[][] A) method. 
     * @see #solve(double[][])
     */
    public static void init(){
        JFrame mainFrame = new JFrame();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

        JLabel label = new JLabel("Gaussian elimination method");

        JTextArea matrixArea = new JTextArea("Enter a nxn matrix (nxn+1 for matrix with results); separate values with whitespace;");

        JButton calc = new JButton("calculate");

        JTextArea result = new JTextArea();
        result.setEditable(false);

        calc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] textArea = matrixArea.getText().split("\\r?\\n");
                    int n = textArea.length;
                    int m = n + 1;
                    double[][] matrix = new double[n][n + 1];
                    for (int i = 0; i < n; i++) {
                        String[] textMatrix = textArea[i].split("\\s+");

                        if(textMatrix.length == n){
                            for (int j = 0; j < m; j++) {
                                if(j == m - 1){
                                    matrix[i][j] = 0;
                                    continue;
                                }
                                matrix[i][j] = Double.parseDouble(textMatrix[j]);
                            }
                        } else if(textMatrix.length == m){
                            for (int j = 0; j < m; j++) {
                                matrix[i][j] = Double.parseDouble(textMatrix[j]);
                            }
                        } else {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                    }
                    double[] res = solve(matrix);
                    result.setText("");
                    for(int k = 0; k < res.length; k++){
                        result.append("x" + (k+1) + " = " + res[k] + "\n");
                    }
                } catch (NumberFormatException exception) {
                    result.setText("No suitable matrix format.");
                    return;
                } catch (ArrayIndexOutOfBoundsException exception) {
                    result.setText("Wrong matrix dimensions.");
                    return;
                }
            }
        });

        mainPanel.add(label);
        mainPanel.add(matrixArea);
        mainPanel.add(calc);
        mainPanel.add(result);

        mainFrame.add(mainPanel);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(720,480);
        mainFrame.setVisible(true);
    }

    /**
     * Method solve(double[][] A) solves a NxN matrix with the Gaussian Elimination Method.
     * @see #backTrack(double[][]) is called here.
     * @param A as an NxN Matrix.
     * @return double[] which represents the vector with solved variables.
     */
    public static double[] solve(double[][] A){
        int countVar = A.length; // 3
        for(int i = 0; i < countVar - 1; i++){ // 0 1
            for(int j = 0; j < countVar; j++){ // 0 1
                if(i == i + j || i + j >= countVar){
                    continue;
                }
                double x_1 = A[i][i];
                double x_2 = A[i + j][i];
                if(x_1 == 0 || x_2 == 0) continue; // SWAP OR SOMETHING
                double ratio = x_1 / x_2;
                for(int k = 0; k < countVar + 1; k++){ // 0 1 2
                    A[i+j][k] =  A[i][k] - (A[i+j][k] * ratio);
                }
            }
        }
        return backTrack(A);
    }

    /**
     * Creates the solved vector out of the Gaussian Elimination Method.
     * @param A as a NxN Matrix.
     * @return double[] which represents the vector with solved variables.
     */
    public static double[] backTrack(double[][] A) {
        int countVar = A.length;
        double res[] = new double[countVar];
        for (int i = countVar - 1; i >= 0; i--) {
            res[i] = A[i][countVar];
            for (int j = i + 1; j < countVar; j++) {
                res[i] -= A[i][j] * res[j];
            }
            res[i] = res[i] / A[i][i];
        }
        return res;
    }
}
