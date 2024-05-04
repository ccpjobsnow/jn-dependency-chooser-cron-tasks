package com.ccp.jn.cron.controller;

import com.ccp.dependency.injection.CcpDependencyInjection;
import com.ccp.implementations.db.bulk.elasticsearch.CcpElasticSerchDbBulk;
import com.ccp.implementations.db.crud.elasticsearch.CcpElasticSearchCrud;
import com.ccp.implementations.db.query.elasticsearch.CcpElasticSearchQueryExecutor;
import com.ccp.implementations.db.utils.elasticsearch.CcpElasticSearchDbRequest;
import com.ccp.implementations.email.sendgrid.CcpSendGridEmailSender;
import com.ccp.implementations.file.bucket.gcp.CcpGcpFileBucket;
import com.ccp.implementations.http.apache.mime.CcpApacheMimeHttp;
import com.ccp.implementations.instant.messenger.telegram.CcpTelegramInstantMessenger;
import com.ccp.implementations.json.gson.CcpGsonJsonHandler;
import com.ccp.jn.async.business.factory.CcpJnAsyncBusinessFactory;
import com.ccp.jn.async.business.support.JnAsyncBusinessNotifyError;

public class JnCronTasksController {
	public static void main(String[] args) throws Exception {
		CcpDependencyInjection.loadAllDependencies
		(
				new CcpElasticSearchQueryExecutor(),
				new CcpTelegramInstantMessenger(),
				new CcpElasticSearchDbRequest(),
				new CcpJnAsyncBusinessFactory(),
				new CcpSendGridEmailSender(),
				new CcpElasticSerchDbBulk(),
				new CcpElasticSearchCrud(),
				new CcpGsonJsonHandler(),
				new CcpApacheMimeHttp(),
				new CcpGcpFileBucket()
		);
		String taskName = args[0];
		String parameters = args[1];
		
		CcpCronTasksController.main(JnAsyncBusinessNotifyError.INSTANCE, taskName, parameters);
	}
}
