package com.pinhuba.common.activiti.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinhuba.common.activiti.cmd.CompleteTaskWithCommentCmd;
import com.pinhuba.common.activiti.support.DefaultTaskListener;

public class AutoCompleteFirstTaskListener extends DefaultTaskListener {
	private static Logger logger = LoggerFactory.getLogger(AutoCompleteFirstTaskListener.class);

	@Override
	public void onCreate(DelegateTask delegateTask) throws Exception {
		String userId = Authentication.getAuthenticatedUserId();
		String assignee = delegateTask.getAssignee();

		ProcessDefinitionEntity processDefinitionEntity = Context.getProcessEngineConfiguration().getProcessDefinitionCache().get(delegateTask.getProcessDefinitionId());

		ActivityImpl startActivity = processDefinitionEntity.getInitial();

		if (startActivity.getOutgoingTransitions().size() != 1) {
			throw new IllegalStateException("start activity outgoing transitions cannot more than 1, now is : " + startActivity.getOutgoingTransitions().size());
		}

		PvmTransition pvmTransition = startActivity.getOutgoingTransitions().get(0);
		PvmActivity targetActivity = pvmTransition.getDestination();

		if (!"userTask".equals(targetActivity.getProperty("type"))) {
			logger.debug("first activity is not userTask, just skip");
			return;
		}

		if (targetActivity.getId().equals(delegateTask.getExecution().getCurrentActivityId())) {
			if ((userId != null) && userId.equals(assignee)) {
				logger.debug("auto complete first task : {}", delegateTask);
				new CompleteTaskWithCommentCmd(delegateTask.getId(), null, "发起流程").execute(Context.getCommandContext());
			}
		}
	}
}
