package com.ccp.jn.cron.controller;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.implementations.db.bulk.elasticsearch.Bulk;
import com.ccp.implementations.db.dao.elasticsearch.Dao;
import com.ccp.implementations.db.utils.elasticsearch.DbUtils;
import com.ccp.implementations.db.utils.elasticsearch.Query;
import com.ccp.implementations.emails.sendgrid.Email;
import com.ccp.implementations.file.bucket.gcp.FileBucket;
import com.ccp.implementations.http.apache.mime.Http;
import com.ccp.implementations.instant.messenger.telegram.InstantMessenger;
import com.ccp.jn.cron.tasks.GenericTask;
import com.ccp.process.CcpProcess;
import com.jn.commons.JnEntity;

public class TasksController {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		CcpDependencyInjection.loadAllImplementationsProviders
		(
				new InstantMessenger(),
				new FileBucket(),
				new DbUtils(),
				new Email(),
				new Query(),
				new Http(),
				new Bulk(),
				new Dao()
		);
		JnEntity.loadEntitiesMetadata();
		String taskName = args[0];
		Class<CcpProcess> forName = (Class<CcpProcess>) Class.forName(GenericTask.class.getPackage().getName() + "." + taskName);
		CcpProcess injected = CcpDependencyInjection.getInjected(forName);
		String parameters = args[1];
		CcpMapDecorator mdParameters = new CcpMapDecorator(parameters);
		injected.execute(mdParameters);
	}
}
