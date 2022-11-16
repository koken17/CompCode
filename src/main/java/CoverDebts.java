import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class CoverDebts extends FastInputUtil{
    public CoverDebts(InputStream stream) {
        super(stream);
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastInputUtil in = new FastInputUtil(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskG solver = new TaskG();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskG {
        public void solve(int testNumber, FastInputUtil in, PrintWriter out) {
            int s = in.nextInt();
            List<Integer> debtList = Arrays.stream(in.nextLine().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
            List<Integer> interestList = Arrays.stream(in.nextLine().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
            int[] debts = debtList.stream().mapToInt(i -> i).toArray();
            int[] interests = interestList.stream().mapToInt(i -> i).toArray();

            double res = solution(s,debts,interests);

            out.println(res);
        }

        public double solution(int s,int[] debts,int[] interests){
            double tot = 0.0;
            double fund = s * 0.1;

            List<FinData> finDataList = new ArrayList<>();
            for (int i = 0; i < debts.length; i++) {
                finDataList.add(new FinData(debts[i], interests[i]));
            }


            /* ----- This is the key to solving the problem --- */
            Collections.sort(finDataList,
                    (f1, f2) -> Double.compare(f2.getInterest(), f1.getInterest()));

            int idx = 0;
            while (idx < finDataList.size()) {
                // printFinData(finDataList);
                double currDebt = finDataList.get(idx).getDebt();
                // System.out.println(currDebt);
                if (fund >= currDebt && currDebt > 0.0) {
                    fund -= currDebt;
                    tot += currDebt;
                    finDataList.get(idx).setDebt(0.0);
                    idx++;
                    /* ----- This is the second crucial fix to solving the problem --- */
                    if(fund == 0){
                        fund = s * 0.1;
                        updateDebts(finDataList);
                    }
                } else if (currDebt > 0.0) {
                    finDataList.get(idx).setDebt(currDebt - fund);
                    tot += fund;
                    fund = s * 0.1;
                    updateDebts(finDataList);
                } else {
                    idx++;
                }
            }

            return tot;
        }

        public void printFinData(List<FinData> finDataList){
            System.out.print("[");
            for(FinData f : finDataList){
                System.out.print("(" + f.getDebt() + "," + f.getInterest() + ")");
            }
            System.out.print("]");
            System.out.println();
        }

        public void updateDebts(List<FinData> finDataList) {
            for (FinData finData : finDataList) {
                double currDebt = finData.getDebt();
                double currInterest = finData.getInterest();

                finData.setDebt(currDebt + (currDebt * currInterest)/ 100);
            }
        }

    }

    static class FinData {
        public double debt;
        public double interest;

        public void setDebt(double debt) {
            this.debt = debt;
        }

        public double getInterest() {
            return interest;
        }

        public void setInterest(double interest) {
            this.interest = interest;
        }

        public FinData(double debt, double interest){
            this.debt = debt;
            this.interest = interest;
        }

        public double getDebt() {
            return debt;
        }
    }
}
