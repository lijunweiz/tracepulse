package io.github.lijunweiz.tracepulse.thread;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 线程分析器，从线程名称分析出线程池，并根据线程状态进行分类统计
 * @author lijunwei
 */
public class ThreadAnalyzer {

    /**
     * 汇总线程池名称
     */
    private static final String THREAD_POOL_NAME_SUMMARY = "Summary";

    /**
     * JVM线程池名称
     */
    private static final String THREAD_POOL_NAME_JVM = "JVM";

    /**
     * 未知线程池名称
     */
    private static final String THREAD_POOL_NAME_UNKNOWN = "UNKNOWN";


    /**
     * jkd自身创建线程
     */
    private static final List<String> JVM_THREAD_LIST = Arrays.asList("main",
            "Reference Handler",
            "Finalizer",
            "Signal Dispatcher",
            "Attach Listener",
            "Common-Cleaner");

    private Map<String, ThreadPoolInfo> threadPoolInfoCache = null;

    public ThreadAnalyzer(SamplingProperties properties) {
        if (Objects.isNull(properties)) {
            properties = new SamplingProperties();
        }
        ScheduledExecutorService threadAnalyzer = Executors.newScheduledThreadPool(1,
                new NamedThreadFactory("ThreadAnalyzer"));
        threadAnalyzer.scheduleWithFixedDelay(
                () -> this.threadPoolInfoCache = this.threadPoolInfo(),
                properties.getInitialDelay(),
                properties.getDelay(),
                TimeUnit.SECONDS
        );
    }

    /**
     * 获取系统中所有线程，并根据名称、状态进行分类
     * @return 分类后的线程池信息
     */
    public Map<String, ThreadPoolInfo> getThreadPoolInfoCache() {
        return this.threadPoolInfoCache;
    }

    /**
     * 获取系统中所有线程，并根据名称、状态进行分类
     * @return 分类后的线程池信息
     */
    public Map<String, ThreadPoolInfo> threadPoolInfo() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfoArray = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds());
        return this.threadInfoToPoolMap(threadInfoArray);
    }

    /**
     * 根据指定线程信息进行分组
     * @param threadInfoArray 线程信息数组
     * @return 分类后的线程池信息
     */
    public Map<String, ThreadPoolInfo> threadInfoToPoolMap(ThreadInfo[] threadInfoArray) {
        if (ArrayUtils.isEmpty(threadInfoArray)) {
            return Collections.emptyMap();
        }

        Map<String, ThreadPoolInfo> threadPoolInfoMap = new HashMap<>();

        List<ThreadInfo> summary = Arrays.stream(threadInfoArray)
                .collect(Collectors.toList());
        threadPoolInfoMap.put(THREAD_POOL_NAME_SUMMARY,
                this.threadInfoToThreadPool(THREAD_POOL_NAME_SUMMARY, summary));

        Map<String, List<ThreadInfo>> grouped = summary.stream()
                .collect(Collectors.groupingBy(this::getThreadPoolName));
        List<ThreadInfo> unknown = new ArrayList<>();
        grouped.forEach((name, list) -> {
            if (CollectionUtils.isNotEmpty(list)) {
                if (Objects.equals(name, THREAD_POOL_NAME_UNKNOWN)) {
                    unknown.addAll(list);
                } else {
                    threadPoolInfoMap.put(name, this.threadInfoToThreadPool(name, list));
                }
            }
        });
        threadPoolInfoMap.put(THREAD_POOL_NAME_UNKNOWN,
                this.threadInfoToThreadPool(THREAD_POOL_NAME_UNKNOWN, unknown));

        return threadPoolInfoMap;
    }

    public String getThreadPoolName(ThreadInfo info) {
        return this.getThreadPoolName(info.getThreadName());
    }

    public String getThreadPoolName(String threadName) {
        Objects.requireNonNull(threadName, "threadName is null");
        if (JVM_THREAD_LIST.contains(threadName)) {
            return THREAD_POOL_NAME_JVM;
        }

        String threadPoolName = null;
        String[] th = threadName.split("-");
        if (th.length >= 1 && th.length <= 3) { // pool-thread-1
            threadPoolName = th[0];
        } else if (th.length == 4) { // pool-1-thread-1
            threadPoolName = th[0] + "-" + th[1];
        } else if (th.length > 4) { // http-nio-40300-exec-1
            threadPoolName = th[0] + "-" + th[1] + "-" + th[2];
        } else {
            threadPoolName = THREAD_POOL_NAME_UNKNOWN;
        }

        return threadPoolName;
    }

    /**
     * 分状态统计线程池信息
     * @param threadPoolName 线程池名称
     * @param threadInfoList 线程池信息
     * @return 根据状态归类的线程池
     */
    private ThreadPoolInfo threadInfoToThreadPool(String threadPoolName, List<ThreadInfo> threadInfoList) {
        ThreadPoolInfo poolInfo = new ThreadPoolInfo();
        poolInfo.setPoolName(threadPoolName);
        if (CollectionUtils.isEmpty(threadInfoList)) {
            poolInfo.setThreadStateInfoList(Collections.emptyList());
        } else {
            List<ThreadPoolInfo.ThreadStateInfo> infoList = threadInfoList.stream()
                    .collect(Collectors.groupingBy(ThreadInfo::getThreadState, Collectors.counting()))
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(e -> new ThreadPoolInfo.ThreadStateInfo(e.getKey(), e.getValue().intValue()))
                    .collect(Collectors.toList());
            poolInfo.setThreadStateInfoList(infoList);
        }

        return poolInfo;
    }

}
