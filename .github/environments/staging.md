# Environment Configuration for Staging

## Required Secrets

- `AWS_ACCESS_KEY_ID_STAGING`: AWS access key for staging environment
- `AWS_SECRET_ACCESS_KEY_STAGING`: AWS secret key for staging environment
- `AWS_REGION`: AWS region (e.g., us-west-2)
- `DB_PASSWORD_STAGING`: Database password for staging
- `REDIS_PASSWORD_STAGING`: Redis password for staging
- `RABBITMQ_PASSWORD_STAGING`: RabbitMQ password for staging

## Environment Variables

- `ENVIRONMENT`: staging
- `LOG_LEVEL`: DEBUG
- `SPRING_PROFILES_ACTIVE`: staging

## Infrastructure

- **ECS Cluster**: bookstore-staging
- **VPC**: staging-vpc
- **Database**: RDS PostgreSQL (staging instance)
- **Cache**: ElastiCache Redis (staging instance)
- **Load Balancer**: ALB for staging environment

## Deployment Settings

- **Auto-deploy**: true (on develop branch)
- **Required reviewers**: 0
- **Deployment timeout**: 10 minutes

## Health Check Endpoints

- API Gateway: https://staging-api.bookstore.com/actuator/health
- Individual services available via internal load balancer
