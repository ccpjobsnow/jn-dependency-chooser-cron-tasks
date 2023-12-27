package com.ccp.jn.cron.controller;

import java.util.function.Function;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.implementations.db.bulk.elasticsearch.CcpElasticSerchDbBulk;
import com.ccp.implementations.db.dao.elasticsearch.CcpElasticSearchDao;
import com.ccp.implementations.db.query.elasticsearch.CcpElasticSearchQueryExecutor;
import com.ccp.implementations.db.utils.elasticsearch.CcpElasticSearchDbRequest;
import com.ccp.implementations.email.sendgrid.CcpSendGridEmailSender;
import com.ccp.implementations.file.bucket.gcp.CcpGcpFileBucket;
import com.ccp.implementations.http.apache.mime.CcpApacheMimeHttp;
import com.ccp.implementations.instant.messenger.telegram.CcpTelegramInstantMessenger;
import com.ccp.implementations.json.gson.CcpGsonJsonHandler;
import com.ccp.jn.cron.tasks.JnGenericCronTask;

public class JnCronTasksController {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		CcpDependencyInjection.loadAllDependencies
		(
				new CcpTelegramInstantMessenger(),
				new CcpGsonJsonHandler(),
				new CcpGcpFileBucket(),
				new CcpElasticSearchDbRequest(),
				new CcpSendGridEmailSender(),
				new CcpElasticSearchQueryExecutor(),
				new CcpApacheMimeHttp(),
				new CcpElasticSerchDbBulk(),
				new CcpElasticSearchDao()
		);
		String taskName = args[0];
		Class<Function<CcpMapDecorator, CcpMapDecorator>> forName = (Class<Function<CcpMapDecorator, CcpMapDecorator>>) Class.forName(JnGenericCronTask.class.getPackage().getName() + "." + taskName);
		Function<CcpMapDecorator, CcpMapDecorator> injected = forName.getDeclaredConstructor().newInstance();
		String parameters = args[1];
		CcpMapDecorator mdParameters = new CcpMapDecorator(parameters);
		injected.apply(mdParameters);
	}
}
