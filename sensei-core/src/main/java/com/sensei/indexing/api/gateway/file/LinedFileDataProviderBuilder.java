package com.sensei.indexing.api.gateway.file;

import java.io.File;
import java.util.Comparator;

import org.apache.commons.configuration.Configuration;
import org.json.JSONObject;

import proj.zoie.impl.indexing.StreamDataProvider;
import proj.zoie.impl.indexing.ZoieConfig;

import com.sensei.dataprovider.file.LinedJsonFileDataProvider;
import com.sensei.indexing.api.DataSourceFilter;
import com.sensei.indexing.api.gateway.SenseiGateway;

public  class LinedFileDataProviderBuilder extends SenseiGateway<String>{

	public static final String name = "file";
	private Comparator<String> _versionComparator;
	public LinedFileDataProviderBuilder(Configuration conf){
	  super(conf);
	  _versionComparator = ZoieConfig.DEFAULT_VERSION_COMPARATOR;
	}

	@Override
	public StreamDataProvider<JSONObject> buildDataProvider(DataSourceFilter<String> dataFilter,
			String oldSinceKey) throws Exception{

	  Configuration myConf = _conf.subset(name);
		String path = myConf.getString("path");
		long offset = oldSinceKey == null ? 0L : Long.parseLong(oldSinceKey);


		LinedJsonFileDataProvider provider = new LinedJsonFileDataProvider(_versionComparator, new File(path), offset);
		if (dataFilter!=null){
		  provider.setFilter(dataFilter);
		}
		return provider;
	}

  @Override
  public Comparator<String> getVersionComparator() {
    return _versionComparator;
  }
}
