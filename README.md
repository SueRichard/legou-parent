# legou-parent

This documentation is aimed to introduce legou-parent. More details can be added by making pull requests.

----------

# What's about legou-parent

Legou-parent is a aggregation project. You can download it and make it as basic E-commerce back-end project. Base on it development, you can customize on your own platform.

----------


# Quick Start

Startup sequence:

The project uses spring cloud config, if you hava any self module please add your own configuration/yml (and so on) file to `config-repo` directory first.

- 1.registryApplication
- 2.configApplication
- 3.gatewayApplication&authApplication
- 4.other(other server do not hava prioritization, unless yourself modules hava order)

----------


# Module Description

- auth-center : The authentication server. Response for issue and check token valid. The user/role information come from security.
- config: Config center. Manage all server configuration files. Such as *.yml .etc
- config-repo: All configuration files store.
- gateway: Gateway server, limit request/rate, transmit request to concrete server.
- legou-admin: Organization management. 
- legou-canal: Listening db binlog, if have any changes commit, this server will inform other server(business related) of caching the data to assign-redis.
- legou-common: Common code. Include Util Package.
- legou-core: Core code. Include Base class, common data structure(po, dto, service, controller .etc)
- legou-item: Goods Server. Provide goods information. Can add/delete/update good info at here. Also provide service for other services.
- legou-order: Order service. Now include shopping cart data. Order info will be added here in the future.
- legou-page: Static server. Generate static page by redis data from page which has been written before.
- legou-search: Query search condition. For search box in the page(fore-end). Also cache data for accelerate page render speed.
- legou-upload: Upload file server. Support file upload/download service.(using DFS)
- registry: Registry center. All services should be registered here.

# Attention

OAuth2 is used. You can also generate your own public/private key at`auth-center`. And put your public key at`legou-security`.

----------

# Status

Now is developing, please look forward to it. And welcome you participate in.  
Prepare to the next stage  
first stage : complete part of security  
second stage: ali related module wait for development and oss service...
third stage: complete the business module for order service
wait for next plan

----------

# Version

mysql : 5.7

jdk: 8_141 

----------

# Learn it & Contact us

We always welcome new contributions, whether for trivial cleanups, big new features or other material rewards, more details will be added as soon.
Any issues can be pulled request or sent to `1352570239@qq.com`. Thank you.

----------
# License
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html) Copyright (C) Apache Software Foundation
