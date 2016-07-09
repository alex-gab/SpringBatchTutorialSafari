package io.spring.batch.listener;


import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public final class JobListener implements JobExecutionListener {

    private JavaMailSender mailSender;

    @Value("${mail.to}")
    private String mailTo;

    @Autowired
    public JobListener(final JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public final void beforeJob(final JobExecution jobExecution) {
        final String jobName = jobExecution.getJobInstance().getJobName();

        final SimpleMailMessage mail = getSimpleMailMessage(String.format("%s is starting", jobName),
                String.format("Per your request, we are informing you that %s is starting",
                        jobName));
        mailSender.send(mail);

    }

    @Override
    public final void afterJob(final JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();

        SimpleMailMessage mail =
                getSimpleMailMessage(String.format("%s has completed", jobName),
                        String.format("Per your request, we are informing you that %s has completed",
                                jobName));

        mailSender.send(mail);
    }

    private SimpleMailMessage getSimpleMailMessage(final String subject, final String text) {
        final SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(mailTo);
        mail.setSubject(subject);
        mail.setText(text);
        return mail;

    }
}
