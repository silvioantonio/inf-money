package com.silvio.infmoney.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.silvio.infmoney.event.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {
		HttpServletResponse response = event.getHttpServletResponse();
		Long codigo = event.getCodigo();
		
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequestUri()
				.path("/{codigo}")
				.buildAndExpand(codigo).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
	}

}
