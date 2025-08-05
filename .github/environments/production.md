# Environment Configuration for Production

## Required Secrets

- `AWS_ACCESS_KEY_ID_PROD`: AWS access key for production environment
- `AWS_SECRET_ACCESS_KEY_PROD`: AWS secret key for production environment
- `AWS_REGION`: AWS region (e.g., us-west-2)
- `DB_PASSWORD_PROD`: Database password for production
- `REDIS_PASSWORD_PROD`: Redis password for production
- `RABBITMQ_PASSWORD_PROD`: RabbitMQ password for production
- `DATADOG_API_KEY`: Datadog API key for monitoring
- `NEW_RELIC_LICENSE_KEY`: New Relic license key for APM

## Environment Variables

- `ENVIRONMENT`: production
- `LOG_LEVEL`: INFO
- `SPRING_PROFILES_ACTIVE`: production

## Infrastructure

- **ECS Cluster**: bookstore-production
- **VPC**: production-vpc
- **Database**: RDS PostgreSQL (production instance with read replicas)
- **Cache**: ElastiCache Redis (production cluster)
- **Load Balancer**: ALB with SSL termination
- **CDN**: CloudFront distribution
- **Monitoring**: DataDog, New Relic

## Deployment Settings

- **Auto-deploy**: false (manual approval required)
- **Required reviewers**: 2
- **Deployment timeout**: 20 minutes
- **Blue-Green deployment**: enabled
- **Rollback strategy**: automatic on health check failure

## Health Check Endpoints

- API Gateway: https://api.bookstore.com/actuator/health
- Individual services monitored via internal health checks

## Security

- **WAF**: AWS WAF enabled
- **Security Groups**: Restrictive inbound rules
- **Encryption**: At rest and in transit
- **Secrets**: AWS Secrets Manager

## Monitoring and Alerting

- **Uptime monitoring**: Pingdom, DataDog
- **Performance monitoring**: New Relic APM
- **Log aggregation**: ELK stack
- **Alerts**: Slack, PagerDuty for critical issues
