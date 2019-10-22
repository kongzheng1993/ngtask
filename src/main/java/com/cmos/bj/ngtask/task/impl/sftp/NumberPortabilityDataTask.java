package com.cmos.bj.ngtask.task.impl.sftp;

import com.cmos.bj.ngtask.model.SftpCfg;
import com.cmos.bj.ngtask.repository.SftpCfgRepository;
import com.cmos.bj.ngtask.task.AbsSftpTask;
import com.cmos.bj.ngtask.utils.SftpUtils;
import com.jcraft.jsch.ChannelSftp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description:
 * @Project: task
 * @Author: kongz
 * @Date: 2019/10/21 10:36
 */
@Component
public class NumberPortabilityDataTask extends AbsSftpTask {

    private static final Logger logger = LoggerFactory.getLogger(NumberPortabilityDataTask.class);

    @Resource
    SftpCfgRepository sftpCfgRepository;


    @Override
    public SftpCfg getSftpCfg() {
        return sftpCfgRepository.getOne(1);
    }

    @Override
    public void doTask() {

        SftpCfg sftpCfg = getSftpCfg();

        //获取sftp连接
        ChannelSftp channelSftp = SftpUtils.connectSftpServer(sftpCfg.getSftpAddr(), sftpCfg.getSftpPort(), sftpCfg.getSftpUserName(), sftpCfg.getSftpUserPasswd(), sftpCfg.getSftpEncoding(), 1);

        boolean result = SftpUtils.downloadFiles(channelSftp, sftpCfg.getSftpRemotePath(), sftpCfg.getSftpLocalPath(), sftpCfg.getFileNameReg(), sftpCfg.getRecursion());

        if (result) {
            logger.info("任务执行完毕：{}", this.getClass());
        } else {
            logger.info("任务执行失败：{}", this.getClass());
        }


    }
}
