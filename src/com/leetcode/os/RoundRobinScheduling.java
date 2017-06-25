package com.leetcode.os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by charles on 6/22/17.
 * implement Round Robin Scheduling algo
 * http://www.geeksforgeeks.org/program-round-robin-scheduling-set-1/
 */
public class RoundRobinScheduling {
    /**
     * For each process,
     * final completion time will be accumulated while round robin moving
     *
     * only when remaining burst time is zero, then to calculate waiting time
     * use current completion time and original given burst time
     *
     * Waiting time = final completion time - original burst time
     * Turnaround time = final completion time
     *
     * TurnAround time : Its the total time taken by the process between starting and the completion
     * Waiting Time : Its the time for which process is ready to run but not executed by CPU scheduler
     *
     * Waiting time of each process can also be calculated as
     * Sum of start service time of current process - last ending as arrival time of same process at previous
     *
     *  p1    p2     p3   p1    p2    p3      p2
     * [0,1] [2,3] [4,5] [6,7] [8,9] [10,11] [12,13]
     *
     * p1 waiting : 6-1
     * p2 waiting : (8-1) + (12-9)
     * p3 waiting : 10-5
     *
     * @param p id of each process
     * @param n number of processes
     * @param bt burst time
     * @param quantum fix interval to execute process
     *
     *
     * github: https://github.com/thirab/OS_Lab1/blob/master/RoundRobin.java
     * geekforgeek: http://www.geeksforgeeks.org/program-round-robin-scheduling-set-1/
     */
    public Process[] roundRobin(Process[] p, int n, int[] bt, int quantum) {
        // init;
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            p[i].id = i;
            p[i].burstTime = bt[i];
        }
        boolean isFinished = true; // flag var to stop process
        Process process = null;
        int completeTime = 0;
        while (true) {
            isFinished = true;
            // traverse all processes one by one repeatedly
            for (int i = 0; i < n; i++) {
                process = p[i];
                if (process.burstTime == 0) {
                    continue;
                }
                isFinished = false; // mark there is pending process
                if (process.burstTime > quantum) {
                    completeTime += quantum;
                    process.burstTime -= quantum;
                } else {
                    completeTime += process.burstTime;
                    process.burstTime = 0;
                    process.waitTime = completeTime - bt[i];
                }
            }
            print(p);
            System.out.println("--------------------");
            // all processes are done;
            if (isFinished) {
                break;
            }
        }
        for (int i = 0; i < n; i++) {
            process = p[i];
            process.burstTime = bt[i];
            process.turnaroundTime = process.burstTime + process.waitTime;
        }
        System.out.println("++++++++++++++");
        print(p);
        return p;
    }

    public void findAvgTime(Process[] p, int n, int quantum) {
        int total_waiting = 0;
        int total_turnaround = 0;
        Process process = null;
        for (int i = 0; i < n; i++) {
            process = p[i];
            total_waiting += process.waitTime;
            total_turnaround += process.turnaroundTime;
        }
        float avgWait = 1.0f * total_waiting / n;
        float avgTat = 1.0f * total_turnaround / n;
        System.out.println("Average waiting : " + avgWait);
        System.out.println("Average turn around : " + avgTat);
    }

    public static void main(String[] args) {
        RoundRobinScheduling r = new RoundRobinScheduling();

        int[] bt1 = {10,5,8};
        int n1 = bt1.length;
        Process[] p = new Process[n1];
        r.roundRobin(p, n1, bt1, 2);
        r.findAvgTime(p, n1, 2);

        int[] at1 = {0,0,0};
        r.roundRobinII(bt1, at1, n1, 2);
    }

    private void print(Process[] t) {
        Arrays.stream(t).forEach(a-> System.out.println(a));
        System.out.println();
    }

    /**
     * below method without using Process class array
     * instead, use four int array to store details
     * also with arrival time
     * Arrival is the time when process has arrived the list.
     * http://www.onlineclassnotes.com/2011/12/explain-and-solve-round-robin-rr-of.html?ref=Content%20Body
     */
    public void roundRobinII(int[] burst_time, int[] arrival_time, int n, int quantum) {
        int[] wait_time = new int[n];
        int[] turnaround_time = new int[n];
        wait_time[0] = 0;

        for (int i = 1; i < n; i++) {
            wait_time[i] = wait_time[i-1] + burst_time[i-1];
            wait_time[i] = wait_time[i] - arrival_time[i];
        }
        int total_waiting = 0;
        int total_turnaround = 0;
        for (int i = 0; i < n; i++) {
            turnaround_time[i] = wait_time[i] + burst_time[i];
            total_waiting += wait_time[i];
            total_turnaround += turnaround_time[i];
        }
        // display
        System.out.println(String.format("pid\tburst time\twait time\tturn around\t arrival time "));
        for (int i = 0; i < n; i++) {
            System.out.println(String.format("%d \t %d \t %d \t %d \t %d", i, burst_time[i], wait_time[i], turnaround_time[i], arrival_time[i]));
        }
        float avgWait = 1.0f * total_waiting / n;
        float avgTat = 1.0f * total_turnaround / n;
        System.out.println("avg wait " + avgWait);
        System.out.println("avg turn around " + avgTat);
    }

}
class Process {
    int id;
    int burstTime;
    int waitTime;
    int turnaroundTime;
    public Process() {
        burstTime = 0;
        waitTime = 0;
        turnaroundTime = 0;
    }

    @Override
    public String toString() {
        return "Process{" +
                "id=" + id +
                ", burstTime=" + burstTime +
                ", waitTime=" + waitTime +
                ", turnaroundTime=" + turnaroundTime +
                '}';
    }
}
