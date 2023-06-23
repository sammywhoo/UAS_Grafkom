#version 330

//Directional Light
struct DirLight{
    vec3 direction;
    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};
struct PointLight{
  vec3 position;

  float constant;
  float linear;
  float quadratic;

  vec3 ambient;
  vec3 diffuse;
  vec3 specular;
};
#define NR_POINT_LIGHTS 4
uniform PointLight pointLights[NR_POINT_LIGHTS];

struct SpotLight{
  vec3 position;
  vec3 direction;

  float cutOff;
  float outerCutOff;

  vec3 ambient;
  vec3 diffuse;
  vec3 specular;

  float constant;
  float linear;
  float quadratic;
};
uniform SpotLight spotLight;
uniform DirLight dirLight;
out vec4 fragColor;
uniform vec4 uni_color;

//uniform vec3 lightColor;
//uniform vec3 lightPos;
uniform vec3 viewPos;

in vec3 Normal;
in vec3 FragPos;

vec3 CalcDirLight(DirLight light, vec3 normal, vec3 viewDir){
    vec3 lightDir = normalize(-light.direction);

    //diffuse
    float diff = max(dot(normal,lightDir),0.0);
    //specular
    vec3 reflectDir = reflect(-lightDir,normal);
    float spec = pow(max(dot(viewDir, reflectDir),0.0),64.0);

    vec3 ambient = light.ambient;
    vec3 diffuse = light.diffuse * diff;
    vec3 specular = light.specular * spec;
    return(ambient + diffuse + specular);
}

vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir)
{
    vec3 lightDir = normalize(light.position - fragPos);
    //diffuse shading
    float diff = max(dot(normal, lightDir), 0.0);
    //specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0),3072.0);
    //attenuation
    float distance    = length(light.position - fragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance +
    light.quadratic * (distance * distance));
    //combine results
    vec3 ambient  = light.ambient;
    vec3 diffuse  = light.diffuse  * diff;
    vec3 specular = light.specular * spec;
    ambient  *= attenuation;
    diffuse  *= attenuation;
    specular *= attenuation;
    return (ambient + diffuse + specular);
}

vec3 CalcSpotLight(SpotLight light, vec3 normal, vec3 fragPos, vec3 viewDir)
{

    //diffuse shading
    vec3 lightDir = normalize(light.position - fragPos);
    float diff = max(dot(normal, lightDir), 0.0);

    //specular shading
    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0),3072.0);

    //attenuation
    float distance    = length(light.position - fragPos);
    float attenuation = 1.0 / (light.constant + light.linear * distance +
    light.quadratic * (distance * distance));

    //spotlight intensity
    float theta     = dot(lightDir, normalize(-light.direction));
    float epsilon   = light.cutOff - light.outerCutOff;
    float intensity = clamp((theta - light.outerCutOff) / epsilon, 0.0, 1.0);

    //combine results
    vec3 ambient = light.ambient;
    vec3 diffuse = light.diffuse * diff;
    vec3 specular = light.specular * spec;
    ambient  *= attenuation;
    diffuse  *= attenuation * intensity;
    specular *= attenuation * intensity;
    return (ambient + diffuse + specular);
}

void main()
{
//    //vec4(red,green,blue,alpha)
//    //rgba -> red 100/255
//    //fragColor = vec4(1.0,0.0,0.0,1.0);
//    float ambientStrength = 0.3f;
//    vec3 ambient = ambientStrength *lightColor;
//    //Diffuse
//    vec3 LightDirection = normalize(lightPos-FragPos);
//    float diff = max(dot(Normal, LightDirection),0.0);
//    vec3 diffuse = diff * lightColor;
//
//    //specular
//    float specularStrength = 0.5f;
//    vec3 viewDir = normalize(viewPos - FragPos);
//
//    //originial phong
////    vec3 reflectDir = reflect(-LightDirection, Normal);
////    float spec = pow(max(dot(viewDir, reflectDir),0.0),64.0);
//
//    //blinn phong
//    vec3 halfwayDir = normalize(LightDirection + viewDir);
//    float spec = pow(max(dot(Normal, halfwayDir),0.0),64.0*3.0);
//
//    vec3 specular = specularStrength * spec * lightColor;
//
//
//    vec3 result = (ambient+diffuse+specular) * vec3(uni_color);
//fragColor = vec4(result,1.0f);

    //properties
    vec3 normal = normalize(Normal);
    vec3 viewDir = normalize(viewPos - FragPos);
    //directional light
    vec3 result = CalcDirLight(dirLight, normal, viewDir);

    //point lights
    for(int i = 0; i < NR_POINT_LIGHTS; i++){
        result += CalcPointLight(pointLights[i], normal, FragPos, viewDir);
    }

    //spot light
    result += CalcSpotLight(spotLight, normal, FragPos, viewDir);

fragColor = vec4(result * vec3(uni_color),1.0f);
}
