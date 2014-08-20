function [ output_clipper ] = clipper( output_filter123 )

output_clipper = zeros (2192,140);

counter=1;

for m =101:1:2292
    
    output_clipper(counter,:)=output_filter123(m,:);
    counter=counter+1;

end

