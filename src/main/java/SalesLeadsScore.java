import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class SalesLeadsScore extends FastInputUtil {

    public SalesLeadsScore(InputStream stream) {
        super(stream);
    }

    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastInputUtil in = new FastInputUtil(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        SalesLeadsScore.TaskG solver = new SalesLeadsScore.TaskG();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskG {
        public void solve(int testNumber, FastInputUtil in, PrintWriter out) {
            String s = in.nextLine();
            List<String> namesList = Arrays.stream(s.split("\\s+")).collect(Collectors.toList());
            List<Integer> timeList = Arrays.stream(in.nextLine().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
            List<Integer> netValList = Arrays.stream(in.nextLine().split("\\s+")).map(Integer::parseInt).collect(Collectors.toList());
            List<Boolean> vacationList = Arrays.stream(in.nextLine().split("\\s+")).map(Boolean::parseBoolean).collect(Collectors.toList());

            String[] names = namesList.toArray(new String[0]);
            int[] time = timeList.stream().mapToInt(i -> i).toArray();
            int[] netValue = netValList.stream().mapToInt(i -> i).toArray();
            boolean[] isOnVacation = new boolean[vacationList.size()];

            int idx = 0;
            for (Boolean b : vacationList) {
                isOnVacation[idx++] = b.booleanValue();
            }

            String[] result = solution(names, time, netValue, isOnVacation);

            for(int i = 0;i < result.length;i++){
                System.out.print(" ");
                System.out.print(result[i]);
            }

            System.out.println();

        }

        public String[] solution(String[] names, int[] time, int[] netValue, boolean[] isOnVacation) {
            List<Lead> leadList = new ArrayList<>();
            for (int i = 0; i < names.length; i++) {
                leadList.add(new Lead(names[i], time[i], netValue[i], isOnVacation[i]));
            }

            List<Lead> sortedList = sortLeadList(leadList);

            return sortedList.stream().map(Lead::getName).collect(Collectors.toList()).toArray(new String[0]);
        }

        public List<Lead> sortLeadList(List<Lead> leadList) {

            List<Lead> filteredListByVacationStatus = leadList.stream().filter(lead -> !lead.getOnVacation())
                    .collect(Collectors.toList());
            Collections.sort(filteredListByVacationStatus, new Lead.CustomComparator());
            return filteredListByVacationStatus;
        }

    }

    static class Lead implements Comparable<Lead> {
        public String name;
        public Integer time;
        public Integer netValue;
        public Boolean isOnVacation;

        public Lead(String name, Integer time, Integer netValue, Boolean isOnVacation) {
            this.name = name;
            this.time = time;
            this.netValue = netValue;
            this.isOnVacation = isOnVacation;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }

        public Integer getNetValue() {
            return netValue;
        }

        public void setNetValue(Integer netValue) {
            this.netValue = netValue;
        }

        public Boolean getOnVacation() {
            return isOnVacation;
        }

        public void setOnVacation(Boolean onVacation) {
            isOnVacation = onVacation;
        }

        public int compareTo(Lead lead) {
            double currLeadScore = (this.getNetValue() * this.getTime()) / 365.0;
            double otherLeadScore = (lead.getNetValue() * lead.getTime()) / 365.0;

            if (!doubleEquals(otherLeadScore, currLeadScore)) {
                return Double.compare(otherLeadScore, currLeadScore);
            } else if (this.getTime().intValue() != lead.getTime().intValue()) {

                return lead.getTime().intValue() - this.getTime().intValue();
            } else {
                // System.out.println("Equal3");
                return this.getName().compareTo(lead.getName());
                // return compareCustom(this.getName(),lead.getName());
            }
        }

        public boolean doubleEquals(double d1, double d2) {
            double epsilon = 0.000001d;
            return Math.abs(d1 - d2) < epsilon;
        }

        public static class CustomComparator implements Comparator<Lead> {
            public int compare(Lead o1, Lead o2) {
                return o1.compareTo(o2);
            }
        }

    }
}
