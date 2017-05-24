package app.washtime.com.washtime.task.impl;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

import app.washtime.com.washtime.constants.ConfigConstants;
import app.washtime.com.washtime.entity.Rule;
import app.washtime.com.washtime.task.RuleTask;

public class RuleTaskImpl implements RuleTask {

    @Override
    public Rule getRule(String locationName, String name, String floor) {
        try {
            return new GetRule().execute(locationName, name, floor).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String saveRule(Rule rule) {
        try {
            return new CreateNewRule().execute(rule).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class GetRule extends AsyncTask<String, Void, Rule> {

        @Override
        protected Rule doInBackground(String... params) {
            try {
                final String url = "http://" + ConfigConstants.IP + "/getRule" + "/" + params[0] + "/" + params[1] + "/" + Integer.valueOf(params[2]);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Rule rule = restTemplate.getForObject(url, Rule.class);
                return rule;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
    }

    private class CreateNewRule extends AsyncTask<Rule, Void, String> {

        @Override
        protected String doInBackground(Rule... params) {
            final String url = "http://" + ConfigConstants.IP + "/saveRule";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            ResponseEntity<String> postResponse = restTemplate.postForEntity(url, params[0], String.class);
            return postResponse.getStatusCode().toString();
        }
    }
}
