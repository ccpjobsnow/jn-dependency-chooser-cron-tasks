package com.ccp.jn.cron.controller;

import java.util.function.Function;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.dependency.injection.CcpInstanceInjection;
import com.ccp.implementations.db.bulk.elasticsearch.Bulk;
import com.ccp.implementations.db.dao.elasticsearch.Dao;
import com.ccp.implementations.db.utils.elasticsearch.DbUtils;
import com.ccp.implementations.db.utils.elasticsearch.Query;
import com.ccp.implementations.emails.sendgrid.Email;
import com.ccp.implementations.file.bucket.gcp.FileBucket;
import com.ccp.implementations.http.apache.mime.Http;
import com.ccp.implementations.instant.messenger.telegram.InstantMessenger;
import com.ccp.implementations.text.extractor.apache.tika.JsonHandler;
import com.ccp.jn.cron.tasks.GenericTask;

public class TasksController {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		CcpInstanceInjection.loadAllInstances
		(
				new InstantMessenger(),
				new JsonHandler(),
				new FileBucket(),
				new DbUtils(),
				new Email(),
				new Query(),
				new Http(),
				new Bulk(),
				new Dao()
		);
		String taskName = args[0];
		Class<Function<CcpMapDecorator, CcpMapDecorator>> forName = (Class<Function<CcpMapDecorator, CcpMapDecorator>>) Class.forName(GenericTask.class.getPackage().getName() + "." + taskName);
		Function<CcpMapDecorator, CcpMapDecorator> injected = forName.newInstance();
		String parameters = args[1];
		CcpMapDecorator mdParameters = new CcpMapDecorator(parameters);
		injected.apply(mdParameters);
	}
}
