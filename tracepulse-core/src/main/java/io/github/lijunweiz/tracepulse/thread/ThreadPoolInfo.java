package io.github.lijunweiz.tracepulse.thread;

import java.util.List;

/**
 * @author lijunwei
 */
public class ThreadPoolInfo {

    private String poolName;

    private List<ThreadStateInfo> threadStateInfoList;

    public String getPoolName() {
        return poolName;
    }

    public ThreadPoolInfo setPoolName(String poolName) {
        this.poolName = poolName;
        return this;
    }

    public List<ThreadStateInfo> getThreadStateInfoList() {
        return threadStateInfoList;
    }

    public ThreadPoolInfo setThreadStateInfoList(List<ThreadStateInfo> threadStateInfoList) {
        this.threadStateInfoList = threadStateInfoList;
        return this;
    }

    @Override
    public String toString() {
        return "ThreadPoolInfo{" +
                "poolName='" + poolName + '\'' +
                ", threadStateInfoList=" + threadStateInfoList +
                '}';
    }

    public static class ThreadStateInfo {
        private Thread.State threadState;
        private int threadCount;

        public ThreadStateInfo() {
        }

        public ThreadStateInfo(Thread.State threadState, int threadCount) {
            this.threadState = threadState;
            this.threadCount = threadCount;
        }

        public Thread.State getThreadState() {
            return threadState;
        }

        public ThreadStateInfo setThreadState(Thread.State threadState) {
            this.threadState = threadState;
            return this;
        }

        public int getThreadCount() {
            return threadCount;
        }

        public ThreadStateInfo setThreadCount(int threadCount) {
            this.threadCount = threadCount;
            return this;
        }

        @Override
        public String toString() {
            return "ThreadStateInfo{" +
                    "threadState=" + threadState +
                    ", threadCount=" + threadCount +
                    '}';
        }
    }
}
