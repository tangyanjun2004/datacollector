/*
 * Copyright 2017 StreamSets Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.stage.origin.salesforce;

import com.streamsets.pipeline.api.Config;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.StageUpgrader;
import com.streamsets.pipeline.lib.salesforce.SubscriptionType;
import com.streamsets.pipeline.lib.salesforce.TestForceInputUpgrader;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class TestForceSourceUpgrader extends TestForceInputUpgrader {
  @Test
  public void testUpgradeV1toV2() throws StageException {
    StageUpgrader.Context context = Mockito.mock(StageUpgrader.Context.class);
    Mockito.doReturn(1).when(context).getFromVersion();
    Mockito.doReturn(2).when(context).getToVersion();

    List<Config> configs = new ArrayList<>();

    ForceSourceUpgrader forceSourceUpgrader = new ForceSourceUpgrader();
    forceSourceUpgrader.upgrade(configs, context);

    Assert.assertEquals(1, configs.size());
    Config config = configs.get(0);
    Assert.assertEquals("forceConfig.subscriptionType", config.getName());
    Assert.assertEquals(SubscriptionType.PUSH_TOPIC, config.getValue());
  }

  @Override
  public StageUpgrader getStageUpgrader() {
    return new ForceSourceUpgrader();
  }

  @Test
  public void testUpgradeV2toV3() throws StageException {
    List<Config> configs = testUpgradeV2toV3Common();

    Assert.assertEquals(0, configs.size());
  }
}
